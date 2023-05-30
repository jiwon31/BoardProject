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
	files: BoardFile[];
	createdAt: string;
	isDeleted: boolean;
}

type BoardFile = {
	id: number;
	originFileName: string;
}