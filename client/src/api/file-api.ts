import { instance } from './axios.config';

export default class FileApi {
    async downloadFile(fileId: number) {
        const response = await instance.get(`/files/${fileId}`, {responseType: "blob"});
        return response;
    }
}