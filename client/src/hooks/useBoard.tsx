import { useMutation, useQuery, useQueryClient } from "@tanstack/react-query";
import BoardApi from "api/board-api";
import { Board, BoardRequest } from "types/board";

export default function useBoard(boardId?: number, boardApi = new BoardApi()) {
  const queryClient = useQueryClient();

  const boardQuery = useQuery<Board[], Error>(
    ["boards"],
    boardApi.getBoardList,
    { staleTime: 1000 * 60, enabled: !boardId }
  );

  const singleBoardQuery = useQuery<Board, Error>(
    ["boards", boardId],
    () => boardApi.getSingleBoard(boardId!),
    { staleTime: 1000 * 60 * 5, enabled: !!boardId }
  );

  const createBoard = useMutation<{ id: number }, Error, BoardRequest>((data) =>
    boardApi.createBoard(data)
  );

  return { boardQuery, singleBoardQuery, createBoard };
}
