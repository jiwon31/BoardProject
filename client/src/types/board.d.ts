export type BoardContent = {
    title: string;
    content: string;
}

export type UpdateBoardRequest = {
	id: number;
	info: BoardContent;
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
	createdAt: string;
	isDeleted: boolean;
}