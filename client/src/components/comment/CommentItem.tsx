import { Comment } from "types/comment";
import { FaUserCircle } from "react-icons/fa";
import { RxDotsVertical } from "react-icons/rx";
import useRecoilUser from "hooks/useRecoilUser";
import { useState } from "react";
import { formatDate } from "util/formatDate";

export default function CommentItem({
  comment,
  comment: { content, userId, userName, isDeleted, createdAt },
}: {
  comment: Comment;
}) {
  const { user } = useRecoilUser();
  const [isDropdown, setIsDropdown] = useState<boolean>(false);

  return (
    <div>
      {!isDeleted ? (
        <li className="py-3 border-b border-gray-300">
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
                      <button>수정</button>
                    </div>
                    <div className="flex items-center px-3 py-2 m-1 mt-0 text-sm text-red-500 whitespace-nowrap">
                      <button>삭제</button>
                    </div>
                  </div>
                )}
              </div>
            )}
          </div>
          <div>
            <pre className="whitespace-pre-wrap font-sans">{content}</pre>
          </div>
          <div>
            <p className="mt-1 text-xs text-gray-400">
              {formatDate(createdAt)}
            </p>
          </div>
        </li>
      ) : (
        <li className="py-6 border-b border-gray-300">삭제된 댓글입니다.</li>
      )}
    </div>
  );
}
