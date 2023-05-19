import { useQueryClient, useMutation, useQuery } from "@tanstack/react-query";
import CommentApi from "api/comment-api";
import {
  Comment,
  CommentContent,
  CreateCommentRequest,
  UpdateCommentRequest,
} from "types/comment";
import useRecoilUser from "./useRecoilUser";

export default function useComment(
  boardId: number,
  commentApi = new CommentApi()
) {
  const { user } = useRecoilUser();
  const queryClient = useQueryClient();

  const commentQuery = useQuery<Comment[], Error>(
    ["comments", { boardId }],
    () => commentApi.getCommentList(boardId),
    { staleTime: 1000 * 60, enabled: !!user }
  );

  const createComment = useMutation<
    { id: number },
    Error,
    CreateCommentRequest
  >((data) => commentApi.createComment(data), {
    onSuccess: updateQueries,
  });

  const updateComment = useMutation<
    CommentContent,
    Error,
    UpdateCommentRequest
  >((data) => commentApi.updateComment(data), {
    onSuccess: updateQueries,
  });

  const deleteComment = useMutation<void, Error, number>(
    (commentId) => commentApi.deleteComment(commentId),
    { onSuccess: updateQueries }
  );

  function updateQueries() {
    queryClient.invalidateQueries(["comments", { boardId }]);
  }

  return { commentQuery, createComment, updateComment, deleteComment };
}