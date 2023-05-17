import { useMutation, useQuery, useQueryClient } from "@tanstack/react-query";
import BoardApi from "api/board-api";
import { Board, BoardRequest } from "types/board";

export default function useBoard(boardApi = new BoardApi()) {
  const queryClient = useQueryClient();

  const boardQuery = useQuery<Board[], Error>(
    ["boards"],
    boardApi.getBoardList,
    { staleTime: 1000 * 60 }
  );

  const createBoard = useMutation<{ id: number }, Error, BoardRequest>((data) =>
    boardApi.createBoard(data)
  );

  return { boardQuery, createBoard };
}
