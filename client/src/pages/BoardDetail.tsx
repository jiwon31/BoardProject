import { useState } from "react";
import useBoard from "hooks/board/useBoard";
import { useNavigate, useParams } from "react-router-dom";
import useRecoilUser from "hooks/useRecoilUser";
import { formatDate } from "util/formatDate";
import { FaUserCircle } from "react-icons/fa";
import { RxDotsVertical } from "react-icons/rx";
import { FaRegCommentDots } from "react-icons/fa";
import CommentContainer from "components/comment/CommentContainer";
import BoardFiles from "components/board/BoardFiles";

export default function BoardDetail() {
  const { id } = useParams();
  const boardId = parseInt(id!);
  const { user } = useRecoilUser();
  const [isDropdown, setIsDropdown] = useState<boolean>(false);
  const [toggleComment, setToggleComment] = useState<boolean>(false);
  const navigate = useNavigate();

  const {
    singleBoardQuery: { isLoading, error, data: board },
    deleteBoard,
  } = useBoard(boardId);

  const handleUpdate = () => navigate(`/boards/update/${boardId}`);

  const handleDelete = () => {
    deleteBoard.mutate(boardId, {
      onError: (error) => alert(error.message),
    });
  };

  return (
    <section className="py-10 max-w-5xl mx-auto">
      {isLoading && <p>Loading...</p>}
      {error && <p>Something is wrong 😣</p>}
      {board && !board.isDeleted && (
        <>
          <div className="flex flex-col mb-20">
            <div className="flex flex-col gap-y-8">
              <h1 className="text-2xl font-bold">{board.title}</h1>
              <div className="flex items-center justify-between pb-3 border-b border-gray-200">
                <div className="flex items-center gap-x-2">
                  <FaUserCircle className="text-4xl text-gray-500" />
                  <div className="flex flex-col">
                    <span className="font-bold">{board.userName}</span>
                    <div className="text-sm text-gray-400">
                      <span className="mr-2">
                        {formatDate(board.createdAt)}
                      </span>
                      <span>조회 {board.viewCount}</span>
                    </div>
                  </div>
                </div>
                {user?.id === board.userId && (
                  <div
                    className="relative px-2 hover:cursor-pointer"
                    onClick={() => setIsDropdown((prev) => !prev)}
                  >
                    <RxDotsVertical className="text-lg" />
                    {isDropdown && (
                      <div className="absolute top-6 right-0 flex flex-col items-center border border-gray-300 bg-white">
                        <div className="flex items-center px-8 py-2 m-1 whitespace-nowrap border-b border-gray-300">
                          <button onClick={handleUpdate}>수정</button>
                        </div>
                        <div className="flex items-center px-8 py-2 m-1 mt-0 text-red-500 whitespace-nowrap">
                          <button onClick={handleDelete}>삭제</button>
                        </div>
                      </div>
                    )}
                  </div>
                )}
              </div>
            </div>
            <div className="pt-8 pb-10">
              <pre className="whitespace-pre-wrap font-sans">
                {board.content}
              </pre>
            </div>
            {board.files.length > 0 && <BoardFiles files={board.files} />}
          </div>
          <div className="mb-8">
            <button
              className="flex items-center gap-x-1 px-2 py-1 border border-gray-300"
              onClick={() => setToggleComment((prev) => !prev)}
            >
              <FaRegCommentDots />
              <span>댓글</span>
            </button>
          </div>
          {toggleComment && <CommentContainer boardId={boardId} />}
        </>
      )}
      {board && board.isDeleted && (
        <div className="flex justify-center text-lg">삭제된 게시글입니다.</div>
      )}
    </section>
  );
}
