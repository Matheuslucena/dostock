import axios from "axios";

const BASE_URL =
  process.env.NODE_ENV === "production"
    ? `${window.location.origin}`
    : "http://127.0.0.1:8080";

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
