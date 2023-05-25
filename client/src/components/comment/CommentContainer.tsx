import useComment from "hooks/comment/useComment";
import CommentInput from "./CommentInput";
import Comments from "./Comments";

export default function CommentContainer({ boardId }: { boardId: number }) {
  const { createComment } = useComment(boardId);

  const handleCreate = (content: string, update: (content: string) => void) =>
    createComment.mutate(
      { boardId, content: { content } },
      {
        onSuccess: () => update(""),
        onError: (error) => alert(error.message),
      }
    );

  return (
    <div className="flex flex-col gap-y-16">
      <Comments boardId={boardId} />
      <CommentInput handleSubmit={handleCreate} />
    </div>
  );
}
