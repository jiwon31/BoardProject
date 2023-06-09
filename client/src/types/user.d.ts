import { Board } from './board';

export type User = {
    id: number;
    email: string;
    userName: string;
}

export type UpdateUserRequest = {
    email: string;
    userName: string;
}

export type GetMyBoardListResponse = {
    isLast: boolean;
    boards: Board[];
}