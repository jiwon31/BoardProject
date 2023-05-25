import { useQuery } from "@tanstack/react-query";
import CommentApi from "api/comment-api";
import { Comment } from "types/comment";

export default function useGetComment(
  boardId: number,
  commentApi = new CommentApi()
) {
  const getCommentList = useQuery<Comment[], Error>(
    ["comments", { boardId }],
    () => commentApi.getCommentList(boardId),
    { staleTime: 1000 * 60 }
  );

  return { getCommentList };
}
