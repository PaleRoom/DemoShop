package ru.ncs.DemoShop.controller.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GetListResponse {
    private List<GetProductResponse> products;
}
