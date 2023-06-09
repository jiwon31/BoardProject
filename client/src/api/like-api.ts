import { GetUserLikedBoardList } from 'types/like';
import { instance } from './axios.config';

export default class LikeApi {
    async addLikeToBoard(boardId: number) {
        const response = await instance.post<string>(`/likes/${boardId}`);
        return response.data;
    }

    async cancelLikeFromBoard(boardId: number) {
        const response = await instance.delete<string>(`/likes/${boardId}`);
        return response.data;
    }

    async getUserLikedBoardList(page?: number) {
        const response = await instance.get<GetUserLikedBoardList>("/users/likes/boards", {params: {page}});
        return response.data;
    }
}