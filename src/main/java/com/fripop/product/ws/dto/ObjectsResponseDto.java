package com.fripop.product.ws.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * Data transfer object used for transferring multiple objects and their pagination information.
 *
 * @since 1.0.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ObjectsResponseDto<T extends Serializable> implements Serializable {

    /**
     * Total number of objects.
     */
    private Long totalNumber;

    /**
     * Collection of objects on the current page.
     */
    private List<T> objects;
}
