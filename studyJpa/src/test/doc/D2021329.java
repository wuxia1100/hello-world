package doc;

public class D2021329 {
    /**
     *是被static关键字修饰的变量，称为静态变量或者类变量，另一种没有被static修饰的对象称为实例变量。
     * 1·、 static应用范围：可用于 内部类、方法和变量。
     *
     * 2、标记一个方法为static，意味着这个方法，被所在类的所有实例公用，在类装载时被首先执行，执行一次即被所有该类的实例共享，同时意味着：
     *
     * A、static方法内部不能引用非static变量。
     *
     * B、static方法不能被子类重写为非static方法。
     *
     * C、父类的非static方法不能被子类重写为static方法。
     *
     * D、static代码块可以用static {}来完成，在类被第一次装载时执行初始化，先于 静态方法和其他方法的执行。
     *
     * 3、标记一个变量为 static，则该变量在内存中有不变的位置，相当于全局变量，所有类的实例都访问同一个存储变量区域。对其修改对于所有类的实例来说都是可见和一致的。
     *
     *
     *
     */
}
