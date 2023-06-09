import { useQueryClient, useMutation } from "@tanstack/react-query";
import CommentApi from "api/comment-api";
import useRecoilUser from "hooks/useRecoilUser";
import {
  CommentContent,
  CreateCommentRequest,
  CreateReplyRequest,
  DeleteCommentRequest,
  UpdateCommentRequest,
} from "types/comment";

export default function useComment(
  boardId: number,
  commentApi = new CommentApi()
) {
  const { user } = useRecoilUser();
  const queryClient = useQueryClient();

  const createComment = useMutation<
    { id: number },
    Error,
    CreateCommentRequest
  >((data) => commentApi.createComment(data), {
    onSuccess: updateQueries,
  });

  const createReply = useMutation<{ id: number }, Error, CreateReplyRequest>(
    (data) => commentApi.createReply(data),
    { onSuccess: updateQueries }
  );

  const updateComment = useMutation<
    CommentContent,
    Error,
    UpdateCommentRequest
  >((data) => commentApi.updateComment(data), {
    onSuccess: () => queryClient.invalidateQueries(["comments", { boardId }]),
  });

  const deleteComment = useMutation<void, Error, DeleteCommentRequest>(
    (data) => commentApi.deleteComment(data),
    { onSuccess: updateQueries }
  );

  function updateQueries() {
    queryClient.invalidateQueries(["comments", { boardId }]);
    queryClient.invalidateQueries(["boards", { boardId }]);
    queryClient.invalidateQueries(["boards", { userId: user?.id }]);
    queryClient.invalidateQueries(["liked-boards", { userId: user?.id }]);
  }

  return {
    createComment,
    createReply,
    updateComment,
    deleteComment,
  };
}
