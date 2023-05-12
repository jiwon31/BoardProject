import { User } from 'types/user';
import { instance } from './axios.config';

export default class UserApi {
    async getUserInfo() {
        const response = await instance.get<User>("/users/info");
        return response.data;
    }

    async updateUserInfo(data: User) {
        const response = await instance.put<User>("/users/info", data);
        return response.data;
    }

    async checkEmailDuplicate(email: string) {
        const response = await instance.post<string>("/users/email/exists", {email});
        return response.data;
    }

    async checkUserNameDuplicate(userName: string) {
        const response = await instance.post<string>("/users/username/exists", {userName});
        return response.data;
    }   
}