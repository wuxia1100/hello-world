package org.cv.sf.dto.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class PageResponse<T> implements Serializable {
    private static final long serialVersionUID = -5378581229285649152L;

    private List<T> content;
    private int start;
    private int counts;
}
