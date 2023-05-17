export type BoardContent = {
    title: string;
    content: string;
}

export type UpdateBoardRequest = {
	id: number;
	info: BoardContent;
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