const CommonValidator = {
  id: {
    regExp: /^[a-zA-Z0-9]{8,32}$/,
    message: "id는 8~32개의 숫자, 문자로 이루어져야 합니다.",
  },
  password: {
    regExp: /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,24}$/,
    message: "비밀번호는 8~24개의 숫자, 문자, 특수문자로 이루어져야 합니다.",
  },
  nickName: {
    regExp: /[a-zA-Z0-9]{2,20}$/,
    message: "닉네임은 2~20개의 숫자, 문자로 이루어져야 합니다.",
  },
  pollTitle: {
    regExp: /^.{4,50}$/,
    message: "투표 주제는 최소 4글자에서 최대 50글자 입니다.",
  },
  validate: (key, target) => {
    const { regExp } = CommonValidator[key];
    return regExp.test(target);
  },
};

export default CommonValidator;
