import { useQueryClient, useMutation } from "@tanstack/react-query";
import CommentApi from "api/comment-api";
import { CreateCommentRequest } from "types/comment";

export default function useComment(commentApi = new CommentApi()) {
  const queryClient = useQueryClient();

  // TODO: 성공 후 댓글리스트 캐시 업데이트
  const createComment = useMutation<
    { id: number },
    Error,
    CreateCommentRequest
  >((data) => commentApi.createComment(data));

  return { createComment };
}
