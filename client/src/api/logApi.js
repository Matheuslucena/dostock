import http from "../utils/http";

export default {
  search(search) {
    return http({
      method: "POST",
      url: "/api/v1/product/log/search",
      data: search,
    });
  },
};
