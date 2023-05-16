import { Button } from "components/ui/Button";
import useUser from "hooks/useUser";
import { useState, useEffect } from "react";
import { UpdateUserRequest } from "types/user";
import { AiFillCheckCircle } from "react-icons/ai";
import useRecoilUser from "hooks/useRecoilUser";

const initialUserInfo = {
  email: "",
  userName: "",
};

export default function Profile() {
  const { user } = useRecoilUser();
  const [userInfo, setUserInfo] = useState<UpdateUserRequest>(initialUserInfo);
  const [updateSuccess, setUpdateSuccess] = useState<boolean | null>(null);
  const { updateUserInfo } = useUser();

  const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    updateUserInfo.mutate(userInfo, {
      onSuccess: () => {
        setUpdateSuccess(true);
        setTimeout(() => setUpdateSuccess(null), 3000);
      },
      onError: (error) => alert(error.message),
    });
  };

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setUserInfo((prev) => ({ ...prev, [name]: value }));
  };

  /** 새로고침 대응 */
  useEffect(() => {
    if (user) {
      setUserInfo((prev) => ({
        ...prev,
        email: user.email,
        userName: user.userName,
      }));
    }
  }, [user]);

  return (
    <section className="py-10 max-w-5xl mx-auto">
      <h1 className="text-2xl font-bold pb-5 mb-5 border-b-2 border-gray-200">
        프로필 수정
      </h1>
      {user && (
        <form className="flex flex-col gap-y-4 px-8" onSubmit={handleSubmit}>
          {updateSuccess && (
            <div className="flex items-center px-5 py-3 bg-green-100 text-green-600">
              <AiFillCheckCircle />
              <p className="ml-1">프로필이 업데이트되었습니다.</p>
            </div>
          )}
          <div className="flex flex-col gap-y-1">
            <label htmlFor="email">이메일</label>
            <input
              id="email"
              type="email"
              name="email"
              value={userInfo.email}
              required
              onChange={handleChange}
            />
          </div>
          <div className="flex flex-col gap-y-1">
            <label htmlFor="userName">닉네임</label>
            <input
              id="userName"
              name="userName"
              value={userInfo.userName}
              required
              onChange={handleChange}
            />
          </div>
          <div className="flex justify-end">
            <Button
              text="변경사항 저장"
              type="submit"
              isLoading={updateUserInfo.isLoading}
            />
          </div>
        </form>
      )}
    </section>
  );
}
