export type BoardRequest = {
    title: string;
    content: string;
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