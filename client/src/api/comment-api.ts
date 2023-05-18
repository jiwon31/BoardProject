import { Comment, CommentContent, CreateCommentRequest, UpdateCommentRequest } from 'types/comment';
import { instance } from './axios.config';

export default class CommentApi {
    async createComment(data: CreateCommentRequest) {
        const response = await instance.post<{id: number}>(`/boards/${data.boardId}/comments`, data.content);
        return response.data;
    }

    async updateComment(data: UpdateCommentRequest) {
        const response = await instance.put<CommentContent>(`/comments/${data.commentId}`, data.content);
        return response.data;
    }

    async deleteComment(commentId: number) {
        const response = await instance.patch<void>(`/comments/${commentId}`);
        return response.data;
    }

    async getCommentList(boardId: number) {
        const response = await instance.get<Comment[]>(`/boards/${boardId}/comments`);
        return response.data;
    }
}