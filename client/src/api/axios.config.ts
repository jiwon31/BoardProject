import axios from "axios";

export const instance = axios.create({
  baseURL: "http://localhost:5000",
  timeout: 30000,
  headers: { "Content-Type": "application/json" },
  withCredentials: true
});

instance.interceptors.response.use(
  (response) => {
    console.log("😍응답 성공", response);
    return response;
  },
  (error) => {
    console.log("😵응답 에러", error.response);
    return Promise.reject(error.response.data);
  }
);

export default axios;
