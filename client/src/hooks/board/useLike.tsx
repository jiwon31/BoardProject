import { useMutation, useQueryClient } from "@tanstack/react-query";
import LikeApi from "api/like-api";
import useRecoilUser from "hooks/useRecoilUser";

export default function useLike(boardId: number, likeApi = new LikeApi()) {
  const { user } = useRecoilUser();
  const queryClient = useQueryClient();

  const addLikeToBoard = useMutation<string, Error, number>(
    (boardId) => likeApi.addLikeToBoard(boardId),
    { onSuccess: updateQuery }
  );

  const cancelLikeFromBoard = useMutation<string, Error, number>(
    (boardId) => likeApi.cancelLikeFromBoard(boardId),
    { onSuccess: updateQuery }
  );

  function updateQuery() {
    queryClient.invalidateQueries(["boards", { boardId }]);
    queryClient.invalidateQueries(["liked-boards", { userId: user?.id }]);
  }

  return { addLikeToBoard, cancelLikeFromBoard };
}
