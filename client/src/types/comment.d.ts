type CommentContent = {
    content: string;
}

export type CreateCommentRequest = {
    boardId: number;
    content: CommentContent;
}

export type CreateReplyRequest = {
    boardId: number;
    commentId: number;
    content: CommentContent;
}

export type UpdateCommentRequest = {
    boardId: number;
    commentId: number;
    content: CommentContent;
}

export type DeleteCommentRequest = {
    boardId: number;
    commentId: number;
}

export type Comment = {
    id: number;
	content: string;
	userId: number;
	userName: string;
	isDeleted: boolean;
	createdAt: string;
    children: Comment[];
}