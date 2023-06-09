import { Board } from './board';

export type GetUserLikedBoardList = {
    isLast: boolean;
    boards: Board[];
}