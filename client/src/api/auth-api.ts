import { LoginRequest, SignUpRequest, Token } from 'types/auth';
import { instance } from './axios.config';

export default class AuthApi {

    static JWT_EXPIRY_TIME = 0.5 * 3600 * 1000; // JWT AccessToken 만료시간 (30분)

    async signUp(data: SignUpRequest) {
        return instance.post<void>("/auth/signup", data);
    }

    async login(data: LoginRequest) {
        instance.defaults.withCredentials = true; // refreshToken을 쿠키로 받기 위해 설정
        const response = await instance.post<Token>("/auth/login", data);
        this.onLoginSuccess(response.data.accessToken);
    }

    async reissue() {
        const response = await instance.post<Token>("/auth/reissue", {
            withCredentials: true,
        });
        this.onLoginSuccess(response.data.accessToken);
    }

    async logout() {
        return instance.post<void>("/auth/logout");
    }

    private onLoginSuccess(accessToken: string) {
        instance.defaults.headers.common["Authorization"] = `Bearer ${accessToken}`;
        setTimeout(this.reissue, AuthApi.JWT_EXPIRY_TIME - 60000); // 토큰 만료되기 1분 전에 새로운 토큰 발급 요청
    }
}