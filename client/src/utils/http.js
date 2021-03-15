import axios from "axios";
import { BASE_URL } from "./config";

const http = axios.create({
  baseURL: BASE_URL,
  headers: {
    "X-Requested-With": "XMLHttpRequest",
  },
  validateStatus(status) {
    return status >= 200 && status < 400;
  },
});

export default http;
