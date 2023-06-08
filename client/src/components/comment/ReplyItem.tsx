import useRecoilUser from "hooks/useRecoilUser";
import { useState } from "react";
import { FaUserCircle } from "react-icons/fa";
import { RxDotsVertical } from "react-icons/rx";
import { CgCornerDownRight } from "react-icons/cg";
import { Comment } from "types/comment";
import { formatDate } from "util/formatDate";
import CommentInput from "./CommentInput";
import useComment from "hooks/comment/useComment";

type ReplyItemProps = {
  reply: Comment;
  boardId: number;
};

export default function ReplyItem({
  reply,
  reply: { id, content, userId, userName, isDeleted, createdAt },
  boardId,
}: ReplyItemProps) {
  const { user } = useRecoilUser();
  const [isDropdown, setIsDropdown] = useState<boolean>(false);
  const [isEditMode, setIsEditMode] = useState<boolean>(false);
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
    <li className="flex flex-col">
      {!isDeleted ? (
        <>
          <div className="flex gap-x-2 py-4 pl-4 border-b border-gray-300">
            <CgCornerDownRight className="text-lg text-gray-400" />
            <div className="flex-1">
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
                          <button
                            onClick={() => setIsEditMode((prev) => !prev)}
                          >
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
                    <pre className="whitespace-pre-wrap font-sans">
                      {content}
                    </pre>
                  </div>
                  <div>
                    <p className="mt-1 text-xs text-gray-400">
                      {formatDate(createdAt)}
                    </p>
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
          </div>
        </>
      ) : (
        <div className="flex gap-x-2 py-6 pl-4 border-b border-gray-300">
          <CgCornerDownRight className="text-lg text-gray-400" />
          <p>삭제된 댓글입니다.</p>
        </div>
      )}
    </li>
  );
}
