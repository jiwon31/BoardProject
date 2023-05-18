import { CreateCommentRequest, UpdateCommentRequest } from 'types/comment';
import { instance } from './axios.config';

export default class CommentApi {
    async createComment(data: CreateCommentRequest) {
        const response = await instance.post(`/boards/${data.boardId}/comments`, data.content);
        return response.data;
    }

    async updateComment(data: UpdateCommentRequest) {
        const response = await instance.put(`/comments/${data.commentId}`, data.content);
        return response.data;
    }

    async deleteComment(commentId: number) {
        const response = await instance.patch(`/comments/${commentId}`);
        return response.data;
    }

    async getCommentList(boardId: number) {
        const response = await instance.get(`/boards/${boardId}/comments`);
        return response.data;
    }
}