CXX = g++
CXXFLAGS = -std=c++11 -pthread
TARGET = server.out
SRCS = server.cpp $(wildcard ./objects/*.cpp)

all: $(TARGET)

$(TARGET): $(SRCS)
	$(CXX) $(CXXFLAGS) -o $(TARGET) $(SRCS)

clean:
	rm -f $(TARGET)