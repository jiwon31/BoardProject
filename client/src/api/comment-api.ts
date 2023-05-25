import { Comment, CommentContent, CreateCommentRequest, CreateReplyRequest, UpdateCommentRequest } from 'types/comment';
import { instance } from './axios.config';

export default class CommentApi {
    async createComment(data: CreateCommentRequest) {
        const { boardId, content } = data;
        const response = await instance.post<{id: number}>(`/boards/${boardId}/comments`, content);
        return response.data;
    }

    async createReply(data: CreateReplyRequest) {
        const { boardId, commentId, content } = data;
        const response = await instance.post<{id: number}>(`/boards/${boardId}/comments/${commentId}`, content);
        return response.data;
    }

    async updateComment(data: UpdateCommentRequest) {
        const { commentId, content } = data;
        const response = await instance.put<CommentContent>(`/comments/${commentId}`, content);
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