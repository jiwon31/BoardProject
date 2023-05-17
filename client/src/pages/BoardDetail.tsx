import { useState } from "react";
import useBoard from "hooks/useBoard";
import { useParams } from "react-router-dom";
import { FaUserCircle } from "react-icons/fa";
import { RxDotsVertical } from "react-icons/rx";
import useRecoilUser from "hooks/useRecoilUser";
import { formatDate } from "util/formatDate";

export default function BoardDetail() {
  const { id } = useParams();
  const {
    singleBoardQuery: { isLoading, error, data: board },
  } = useBoard(parseInt(id!));
  const { user } = useRecoilUser();
  const [isDropdown, setIsDropdown] = useState<boolean>(false);

  return (
    <section className="py-10 max-w-5xl mx-auto">
      {isLoading && <p>Loading...</p>}
      {error && <p>Something is wrong 😣</p>}
      {board && (
        <div className="flex flex-col">
          <div className="flex flex-col gap-y-10">
            <h1 className="text-2xl font-bold">{board.title}</h1>
            <div className="flex items-center justify-between pb-6 border-b border-gray-200">
              <div className="flex items-center gap-x-1">
                <FaUserCircle className="text-2xl text-gray-500" />
                <span className="font-bold">{board.userName}</span>
                <span className="ml-2 text-gray-400">
                  {formatDate(board.createdAt)}
                </span>
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
                        <button>수정</button>
                      </div>
                      <div className="flex items-center px-8 py-2 m-1 mt-0 text-red-500 whitespace-nowrap">
                        <button>삭제</button>
                      </div>
                    </div>
                  )}
                </div>
              )}
            </div>
          </div>
          <div className="py-8">
            <pre className="whitespace-pre-wrap font-sans">{board.content}</pre>
          </div>
        </div>
      )}
    </section>
  );
}
