import { LoginRequest, SignUpRequest, Token } from "types/auth";
import { instance } from "./axios.config";

export default class AuthApi {
  static JWT_EXPIRY_TIME = 0.5 * 3600 * 1000; // JWT AccessToken 만료시간 (30분)

  async signUp(data: SignUpRequest) {
    const response = await instance.post<void>("/auth/signup", data);
    return response.data;
  }

  async login(data: LoginRequest) {
    const response = await instance.post<Token>("/auth/login", data);    
    this.onLoginSuccess(response.data.accessToken);
  }

  async reissue() {
    const response = await instance.post<Token>("/auth/reissue");
    this.onLoginSuccess(response.data.accessToken);
  }

  async logout() {
    await instance.post<void>("/auth/logout");
    delete instance.defaults.headers.common['Authorization'];
  }

  private onLoginSuccess(accessToken: string) {
    instance.defaults.headers.common["Authorization"] = `Bearer ${accessToken}`;
    setTimeout(this.reissue, AuthApi.JWT_EXPIRY_TIME - 60000); // 토큰 만료되기 1분 전에 새로운 토큰 발급 요청
  }
}
