담당자 : 황현수, 오수빈

 

Front) react, nextjs, socket.io, typescript : 황현수

Server) redis, java, netty, socket.io : 오수빈

### frontend
https://github.com/carnad37/example_socket_io_client_nextjs

### 서버 라이브러리
socker.io: https://github.com/mrniko/netty-socketio

### 기초 시나리오 

유저가 페이지 접근

유저에대한 고유키(userKey)를 next.js에서 ssr과 함께 제공.

socket.io를 이용해 서버와 Connect

유저의 접근 또는 이탈에 대해, 서버와 소켓 통신
- 유저 접근 이벤트에 userKey, room 정보 전달 서버에서 해당 정보 소켓 client store에 저장 
- 유저 이탈 시 socket disconnect

클라이언트에서 표시되는 현재 페이지 유저수 업데이트.

![socket-project-scenario](https://github.com/ohsoou/socket-spring-project/assets/64073715/5352aad9-4f9b-4a25-85e8-5a292e28a951)
 

