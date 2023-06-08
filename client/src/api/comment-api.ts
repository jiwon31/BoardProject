import { Comment, CommentContent, CreateCommentRequest,
    CreateReplyRequest, DeleteCommentRequest, UpdateCommentRequest } from 'types/comment';
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
        const { boardId, commentId, content } = data;
        const response = await instance.put<CommentContent>(`/boards/${boardId}/comments/${commentId}`, content);
        return response.data;
    }

    async deleteComment(data: DeleteCommentRequest) {
        const { boardId, commentId } = data;
        const response = await instance.patch<void>(`/boards/${boardId}/comments/${commentId}`);
        return response.data;
    }

    async getCommentList(boardId: number) {
        const response = await instance.get<Comment[]>(`/boards/${boardId}/comments`);
        return response.data;
    }
}