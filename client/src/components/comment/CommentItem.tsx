import { Comment } from "types/comment";
import { FaUserCircle } from "react-icons/fa";
import { RxDotsVertical } from "react-icons/rx";
import useRecoilUser from "hooks/useRecoilUser";
import { useState } from "react";
import { formatDate } from "util/formatDate";
import CommentInput from "./CommentInput";
import ReplyComponent from "./ReplyComponent";
import useComment from "hooks/comment/useComment";

type CommentItemProps = {
  comment: Comment;
  boardId: number;
};

export default function CommentItem({
  comment,
  comment: { id, content, userId, userName, isDeleted, createdAt, children },
  boardId,
}: CommentItemProps) {
  const { user } = useRecoilUser();
  const [isDropdown, setIsDropdown] = useState<boolean>(false);
  const [isEditMode, setIsEditMode] = useState<boolean>(false);
  const [isReplyMode, setIsReplyMode] = useState<boolean>(false);
  const { updateComment, deleteComment } = useComment(boardId);

  const handleUpdate = (content: string) =>
    updateComment.mutate(
      { boardId, commentId: id, content: { content } },
      {
        onSuccess: () => setIsEditMode(false),
        onError: (error) => alert(error.message),
      }
    );

  const handleDelete = () =>
    deleteComment.mutate(
      { boardId, commentId: id },
      {
        onError: (error) => alert(error.message),
      }
    );

  return (
    <li>
      {!isDeleted ? (
        <div className="py-3 border-b border-gray-300">
          <div className="flex justify-between items-center mb-3">
            <div className="flex items-center gap-x-1">
              <FaUserCircle className="text-2xl text-gray-500" />
              <span className="font-semibold">{userName}</span>
            </div>
            {user?.id === userId && (
              <div
                className="relative px-2 hover:cursor-pointer"
                onClick={() => setIsDropdown((prev) => !prev)}
              >
                <RxDotsVertical className="text-lg" />
                {isDropdown && (
                  <div className="absolute top-6 right-0 flex flex-col items-center border border-gray-300 bg-white">
                    <div className="flex items-center px-3 py-2 m-1 text-sm whitespace-nowrap border-b border-gray-300">
                      <button onClick={() => setIsEditMode((prev) => !prev)}>
                        {!isEditMode ? "수정" : "취소"}
                      </button>
                    </div>
                    <div className="flex items-center px-3 py-2 m-1 mt-0 text-sm text-red-500 whitespace-nowrap">
                      <button onClick={handleDelete}>삭제</button>
                    </div>
                  </div>
                )}
              </div>
            )}
          </div>
          {!isEditMode ? (
            <>
              <div className="pr-4">
                <pre className="whitespace-pre-wrap font-sans">{content}</pre>
              </div>
              <div>
                <p className="mt-1 text-xs text-gray-400">
                  {formatDate(createdAt)}
                </p>
              </div>
              <div>
                <button
                  className="flex items-center gap-x-1 px-2 py-1 mt-2.5 border border-gray-300"
                  onClick={() => setIsReplyMode((prev) => !prev)}
                >
                  <span className="text-sm">답글</span>
                </button>
              </div>
            </>
          ) : (
            <div className="pr-4">
              <CommentInput
                commentContent={content}
                handleSubmit={handleUpdate}
              />
            </div>
          )}
        </div>
      ) : (
        <div className="py-6 border-b border-gray-300">삭제된 댓글입니다.</div>
      )}
      <ReplyComponent
        boardId={boardId}
        commentId={id}
        isReplyMode={isReplyMode}
        setIsReplyMode={setIsReplyMode}
        children={children}
      />
    </li>
  );
}
