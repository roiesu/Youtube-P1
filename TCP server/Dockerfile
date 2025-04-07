FROM gcc:13

WORKDIR "/TCP server"

COPY . /tcp

WORKDIR /tcp

RUN make

CMD ["./server.out"]