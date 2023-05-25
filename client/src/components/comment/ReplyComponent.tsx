import { CgCornerDownRight } from "react-icons/cg";
import CommentInput from "./CommentInput";
import useComment from "hooks/comment/useComment";
import { Comment } from "types/comment";
import ReplyItem from "./ReplyItem";

type ReplyComponentProps = {
  boardId: number;
  commentId: number;
  isReplyMode: boolean;
  setIsReplyMode: (data: boolean) => void;
  children: Comment[];
};

export default function ReplyComponent({
  boardId,
  commentId,
  isReplyMode,
  setIsReplyMode,
  children,
}: ReplyComponentProps) {
  const { createReply } = useComment(boardId);

  const handleCreate = (content: string) => {
    createReply.mutate(
      { boardId, commentId, content: { content } },
      {
        onSuccess: () => setIsReplyMode(false),
        onError: (error) => alert(error.message),
      }
    );
  };

  return (
    <div className="bg-gray-50">
      {isReplyMode && (
        <div className="flex gap-x-2 py-4 px-4 border-b border-gray-300">
          <CgCornerDownRight className="text-lg text-gray-400" />
          <div className="flex-1">
            <CommentInput handleSubmit={handleCreate} />
          </div>
        </div>
      )}
      <ul>
        {children.length > 0 &&
          children.map((child) => (
            <ReplyItem key={child.id} reply={child} boardId={boardId} />
          ))}
      </ul>
    </div>
  );
}
