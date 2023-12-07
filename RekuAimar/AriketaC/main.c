#include <string.h>
#include <stdlib.h>
#include <unistd.h>
#include <stdio.h>
#include <fcntl.h>
#include <signal.h>
#include <wait.h>
void manejador(int signal)
{
   printf("Padre recibe señal...%d\n", signal);
   exit(EXIT_SUCCESS);
}


int main()
{
   int pipe_fd[2];                   // File descriptors for the pipe
   pid_t child_pid;                  // Process ID of the child process
   char message[] = "Hello, child!"; // Message to send
   int pipe_fd2[2];                  // File descriptors for the pipe
   pid_t child_pid2;                 // Process ID of the child process
   int zatitzaile[715];
   int contador = 0;
  
    // Create a pipe
   if (pipe(pipe_fd) == -1)
   {
       perror("Pipe creation failed");
       exit(EXIT_FAILURE);
   }
   if (pipe(pipe_fd2) == -1)
   {
       perror("Pipe2 creation failed");
       exit(EXIT_FAILURE);
   }


   // Fork a child process
   child_pid = fork();


   if (child_pid == -1)
   {
       perror("Fork failed");
       exit(EXIT_FAILURE);
   }


   else if (child_pid == 0)
   {
       //  Child process
       //  Do not close the write end of the pipe
       for (int i = 1; i <= 5000; i++)
       {
           if (i % 7 == 0)
           {
               contador++;
           }
       }
       write(pipe_fd[1], &contador, sizeof(int));
       int zatitzailea[contador];
       int contadorea = 0;
       for (int i = 1; i <= 5000; i++)
       {
           if (i % 7 == 0)
           {
               zatitzailea[contadorea] = i;
               contadorea++;
             
           }
       }
        write(pipe_fd[1], &zatitzailea, sizeof(zatitzailea));
       printf("Childcc received message: %d\n", contadorea);
       int received_message[contadorea]; // Buffer to store the received message
                                        
       // Close the read end of the pipe in the child process
       close(pipe_fd[1]);
   }
   else
   {
       // Parent process
       // Close the read end of the pipe
       // Send the message to the child process
       child_pid2 = fork();
       if (child_pid2 == 0)
       {
           long baturaTotal = 0;
           int tamaño_array;
           close(pipe_fd[1]);
           read(pipe_fd[0], &tamaño_array, sizeof(tamaño_array));
           int received_numbers[tamaño_array];
          
           // Buffer to store the received message
          
           read(pipe_fd[0], received_numbers, sizeof(received_numbers));
           close(pipe_fd[0]);
           for (int i = 0; i < tamaño_array; i++)
           {
               baturaTotal += received_numbers[i];
           }
           write(pipe_fd2[1], &baturaTotal, sizeof(long));
           close(pipe_fd2[1]);
       }
       
         signal(SIGINT,manejador);
        signal(SIGTERM,manejador);
       wait(NULL);
       //wait(NULL);
       long result;
       read(pipe_fd2[0], &result, sizeof(long));
       printf("Resultado del padre %ld\n ", result);
 

       // Close the write end of the pipe in the parent process
       close(pipe_fd2[0]);
       
      
      
   }


   return 0;
}

