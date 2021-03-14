import http from "../utils/http";

export default {
  save(product) {
    return http({
      method: "POST",
      url: "api/v1/product/",
      data: product,
    });
  },
  update(product) {
    return http({
      method: "PUT",
      url: `api/v1/product/${product.id}`,
      data: product,
    });
  },
  list() {
    return http({
      method: "GET",
      url: "/api/v1/product/",
    });
  },
  get(id) {
    return http({
      method: "GET",
      url: `/api/v1/product/${id}`,
    });
  },
  foldersList() {
    return http({
      method: "GET",
      url: "api/v1/folder/",
    });
  },
  search(filter) {
    return http({
      method: "POST",
      url: "/api/v1/productSearch/",
      data: filter,
    });
  },
  increase(productId, inventory) {
    return http({
      method: "POST",
      url: `/api/v1/product/${productId}/increase`,
      data: inventory,
    });
  },
  decrease(productId, inventory) {
    return http({
      method: "POST",
      url: `/api/v1/product/${productId}/decrease`,
      data: inventory,
    });
  },
};
