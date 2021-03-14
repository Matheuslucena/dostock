import http from "../utils/http";

export default {
  save(folder) {
    return http({
      method: "POST",
      url: "api/v1/folder/",
      data: folder,
    });
  },
  update(folder) {
    return http({
      method: "PUT",
      url: `api/v1/folder/${folder.id}`,
      data: folder,
    });
  },
  list() {
    return http({
      method: "GET",
      url: "/api/v1/folder/listAll",
    });
  },
};
