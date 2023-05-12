import { useNavigate } from "react-router-dom";
import { Board } from "types/board";
import { formatDate } from "util/formatDate";

type BoardItemProps = { board: Readonly<Board> };

export default function BoardItem({
  board,
  board: { id, title, content, userName, createdAt, isDeleted },
}: BoardItemProps) {
  const navigate = useNavigate();

  return (
    <div>
      {!isDeleted ? (
        <li
          className="py-5 border-b border-gray-300 cursor-pointer"
          onClick={() => navigate(`boards/${id}`)}
        >
          <div>
            <h2 className="text-lg font-bold mb-2">{title}</h2>
            <p className="text-sm text-zinc-400 line-clamp-3">{content}</p>
          </div>
          <div className="mt-2.5">
            <p className="text-sm">{userName}</p>
            <p className="text-xs text-zinc-400">{formatDate(createdAt)}</p>
          </div>
        </li>
      ) : (
        <li className="py-6 border-b border-gray-300">삭제된 게시글 입니다.</li>
      )}
    </div>
  );
}
