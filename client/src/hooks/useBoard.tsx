import { useQuery, useQueryClient } from "@tanstack/react-query";
import BoardApi from "api/board-api";
import { Board } from "types/board";

export default function useBoard(boardapi = new BoardApi()) {
  const queryClient = useQueryClient();

  const boardQuery = useQuery<Board[], Error>(
    ["boards"],
    boardapi.getBoardList,
    { staleTime: 1000 * 60 }
  );

  return { boardQuery };
}
