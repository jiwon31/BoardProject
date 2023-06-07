import { instance } from './axios.config';

export default class LikeApi {
    async addLikeToBoard(boardId: number) {
        const response = await instance.post(`/likes/${boardId}`);
        return response.data;
    }

    async cancelLikeFromBoard(boardId: number) {
        const response = await instance.delete(`/likes/${boardId}`);
        return response.data;
    }

    async getUserLikedBoardList() {
        const response = await instance.get("/users/likes/boards");
        return response.data;
    }
}