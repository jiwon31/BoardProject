import { useMutation, useQuery, useQueryClient } from "@tanstack/react-query";
import BoardApi from "api/board-api";
import { Board, BoardContent, UpdateBoardRequest } from "types/board";
import useRecoilUser from "./useRecoilUser";

export default function useBoard(boardId?: number, boardApi = new BoardApi()) {
  const { user } = useRecoilUser();
  const queryClient = useQueryClient();

  const boardQuery = useQuery<Board[], Error>(
    ["boards"],
    boardApi.getBoardList,
    { staleTime: 1000 * 60, enabled: !boardId }
  );

  const singleBoardQuery = useQuery<Board, Error>(
    ["boards", { boardId }],
    () => boardApi.getSingleBoard(boardId!),
    {
      staleTime: 1000 * 60 * 5,
      enabled: !!boardId && !!user,
    }
  );

  const createBoard = useMutation<{ id: number }, Error, BoardContent>(
    (data) => boardApi.createBoard(data),
    {
      onSuccess: () => {
        queryClient.invalidateQueries(["boards"]);
        queryClient.invalidateQueries(["boards", { userId: user?.id }]);
      },
    }
  );

  const updateBoard = useMutation<Board, Error, UpdateBoardRequest>(
    (data) => boardApi.updateBoard(data),
    { onSuccess: updateQueries }
  );

  const deleteBoard = useMutation<void, Error, number>(
    (id) => boardApi.deleteBoard(id),
    { onSuccess: updateQueries }
  );

  function updateQueries() {
    queryClient.invalidateQueries(["boards", { boardId }]);
    queryClient.invalidateQueries(["boards"]);
    queryClient.invalidateQueries(["boards", { userId: user?.id }]);
  }

  return {
    boardQuery,
    singleBoardQuery,
    createBoard,
    updateBoard,
    deleteBoard,
  };
}
