import { useNavigate } from "react-router-dom";
import { Board } from "types/board";
import { formatDate } from "util/formatDate";
import { HiOutlineHeart, HiHeart } from "react-icons/hi";
import { FaRegCommentDots } from "react-icons/fa";

type BoardItemProps = { board: Readonly<Board> };

export default function BoardItem({
  board,
  board: {
    id,
    title,
    content,
    userName,
    createdAt,
    isDeleted,
    likeCount,
    commentCount,
  },
}: BoardItemProps) {
  const navigate = useNavigate();

  return (
    <div>
      {!isDeleted ? (
        <li
          className="py-5 border-b border-gray-300 cursor-pointer"
          onClick={() => navigate(`/boards/${id}`)}
        >
          <div>
            <h2 className="text-lg font-bold mb-2">{title}</h2>
            <p className="text-sm text-zinc-400 line-clamp-3">{content}</p>
          </div>
          <div className="flex justify-between items-center">
            <div className="mt-2.5">
              <p className="text-sm">{userName}</p>
              <p className="text-xs text-zinc-400">{formatDate(createdAt)}</p>
            </div>
            <div className="flex items-center gap-x-3 px-3 self-end">
              <div className="flex items-center gap-x-1">
                <HiOutlineHeart className="text-xl text-red-500" />
                <span className="text-lg">{likeCount}</span>
              </div>
              <div className="flex items-center gap-x-1 text-lg">
                <FaRegCommentDots className="text-gray-600" />
                <span>{commentCount}</span>
              </div>
            </div>
          </div>
        </li>
      ) : (
        <li className="py-6 border-b border-gray-300">삭제된 게시글입니다.</li>
      )}
    </div>
  );
}
