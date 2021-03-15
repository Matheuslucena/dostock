const BASE_URL =
  process.env.NODE_ENV === "production"
    ? `${window.location.origin}`
    : "http://127.0.0.1:8080";

exports.BASE_URL = BASE_URL;
