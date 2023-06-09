export type BoardContent = {
    title: string;
    content: string;
	files: BoardFile[];
}

export type UpdateBoardRequest = {
	id: number;
	info: FormData;
}

export type GetBoardListResponse = {
	totalPages: number;
	boards: Board[]
}

export type Board = {
    id: number;
	title: string;
	content: string;
	userId: number;
	userName: string;
	files: BoardFile[];
	createdAt: string;
	isDeleted: boolean;
	viewCount: number;
	likeCount: number;
	commentCount: number;
	isLikedByUser: boolean;
}

type BoardFile = {
	id: number;
	originFileName: string;
}