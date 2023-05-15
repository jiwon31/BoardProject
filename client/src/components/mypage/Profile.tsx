import { Button } from "components/ui/Button";
import useRecoilUser from "hooks/useRecoilUser";
import useUser from "hooks/useUser";
import { useState } from "react";
import { UpdateUserRequest } from "types/user";

export default function Profile() {
  const { user } = useRecoilUser();
  const { updateUserInfo } = useUser();
  const [userInfo, setUserInfo] = useState<UpdateUserRequest>({
    email: user!.email,
    userName: user!.userName,
  });

  const handleSubmit = () => {
    updateUserInfo.mutate(userInfo, {
      onSuccess: () => alert("업데이트 완료"),
    });
  };

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setUserInfo((prev) => ({ ...prev, [name]: value }));
  };

  return (
    <section>
      <h1>프로필 수정</h1>
      <div>
        <div>
          <Button text="완료" onClick={handleSubmit} />
        </div>
        <div>
          <div>
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
          <div>
            <label htmlFor="userName">닉네임</label>
            <input
              id="userName"
              name="userName"
              value={userInfo.userName}
              required
              onChange={handleChange}
            />
          </div>
        </div>
      </div>
    </section>
  );
}
