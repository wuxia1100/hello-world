import org.activiti.engine.*;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricProcessInstanceQuery;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.cv.sf.Application;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class TestActiviti {

    @Autowired
    private ProcessEngine processEngine;

    @Test
    public void oneLineStartActiviti(){
        ManagementService managementService = processEngine.getManagementService();
        Map<String, String> properties = managementService.getProperties();
        for (String s : properties.keySet()) {
            System.out.println(s+":"+properties.get(s));
        }
    }

    @Test
    public void deployMyProcess() throws FileNotFoundException {
        RepositoryService repositoryService = processEngine.getRepositoryService();
        //几乎所有需要操作到流程文件，或者读取流程相关信息的，都是使用仓库服务repositoryService
        //部署流程 这里跟构建流程引擎的方式差不多，都是使用了构建者模式
        //1. 首先创建DeploymentBuilder对象,通过这个对象，我们可以指定要加载的流程定义文件，以及一些其他属性。
        DeploymentBuilder deploymentBuilder = repositoryService.createDeployment();
        //2.1 加载流程文件，从类路径加载
//        deploymentBuilder.addClasspathResource("AskForLeave.bpmn");
        //2.使用InputStream加载 (替换自己的文件路径)
        FileInputStream fileInputStream = new FileInputStream("D:\\java\\demo\\study\\studyActiviti\\src\\main\\resources\\process\\OrderAct.bpmn");
        deploymentBuilder.addInputStream("OrderAct.bpmn", fileInputStream);
        deploymentBuilder.name("测试部署请假流程");
        //还有将多个bpmn文件打包批量，以及字符串这两种部署方式（这里暂时不介绍这两种方式）
        //执行部署，直到调用deploy()方法才是真正的部署到引擎中了
        //同时会在act_ge_bytearray ,act_re_deployment ,act_re_procdef这3个表中插入相关信息
        //与以前的教程不一样的是，别的教程中会经常将流程图片和流程bpmn文件一起部署，但我认为有点多余了，因为在部署bpmn的时候会自动生成流程图片
        Deployment deploy = deploymentBuilder.deploy();
        //这个是创建一个部署查询对象查询，查询的是act_re_deployment表这个表记录的就是deploymentBuilder对象所附加的属性
        long count = repositoryService.createDeploymentQuery().count();
        System.out.println("部署时间："+deploy.getDeploymentTime());
        Assert.assertEquals(1, count);
    }

    @Test
    public void startAskLeaveProcess() {
        String LEAVEKEY = "myProcess_1";
        RuntimeService runtimeService = processEngine.getRuntimeService();
        //1.通过流程定义ID启动，这个ID就是act_re_procdef的主键ID 例如Leave:1:4
        //runtimeService.startProcessInstanceById();
        //2.通过流程定义的Key启动，这个Key是在我们画流程图的时候输入的ID
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(LEAVEKEY);
        Assert.assertEquals(LEAVEKEY, processInstance.getProcessDefinitionKey());
        System.out.println("启动时间："+processInstance.getStartTime());
    }

    @Test
    public void listAllProcess(){
        HistoryService historyService = processEngine.getHistoryService();
        //创建Query查询对象 可以在这个对象后面调用各种方法实现条件查询，排序等
        HistoricProcessInstanceQuery     hisProcInstanceQuery = historyService.createHistoricProcessInstanceQuery();
        //不加查询条件
        List<HistoricProcessInstance> processInstances = hisProcInstanceQuery.list();
        for (HistoricProcessInstance processInstance : processInstances) {
            System.out.println(processInstance.getId());
            System.out.println(processInstance.getDeploymentId());
            System.out.println(processInstance.getProcessDefinitionName());
        }
        //根据流程定义名称查询
        List<HistoricProcessInstance> processInstances1 = hisProcInstanceQuery.processDefinitionName("我的流程").list();
        //根据流程实例ID查询
        HistoricProcessInstance processInstance = hisProcInstanceQuery.processInstanceId("2501").singleResult();
        //查询未结束的流程
        List<HistoricProcessInstance> processInstances2 = hisProcInstanceQuery.unfinished().list();
        Assert.assertEquals(1, processInstances1.size());
        Assert.assertNotNull(processInstance);
        Assert.assertEquals(1, processInstances2.size());
    }

    @Test
    public void getTask() {
        HistoryService historyService = processEngine.getHistoryService();
        TaskService taskService = processEngine.getTaskService();
        //如果你的数据库中只有一条流程那么也是可以将list()改为singleResult()的
        List<HistoricProcessInstance> processInstances = historyService
                .createHistoricProcessInstanceQuery().unfinished().list();
        List<Task> tasks = new ArrayList<>();
        for (HistoricProcessInstance processInstance : processInstances) {
            String processInstanceId = processInstance.getId();
            Task task = taskService.createTaskQuery().processInstanceId(processInstanceId).singleResult();
            tasks.add(task);
        }
        tasks.forEach(task -> System.out.println(task.getName()));

        for (Task task : tasks) {
            //TaskService可以完成某个任务的审批，使流程流转到下一节点，比如用户申请审批完成后到达领导审批节点
            //taskService.complete(task.getId());
            Task result = taskService.createTaskQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
            System.out.println(result.getName());
        }
    }

    @Test
    public void completeTask(){
        TaskService taskService = processEngine.getTaskService();
        taskService.complete("10005");
    }
}
