# Word Count Hadoop vs Java Comparison

This Project provides a comparison between implementing the word count functionality using Hadoop and Java. Both Hadoop and Java offer different approaches to handle large-scale data processing tasks, and this document aims to highlight the key differences and advantages of each approach.

Group 11:
1. Aldrian Raffi Wicaksono - 2106653256
2. Aliefya Fikri Ihsani - 2106733843
3. Rizki Awanta Jordhie - 2106655034
4. Shabrina Kamiliya Wiyana - 2106733894

## Hadoop Installation Guide

This guide provides step-by-step instructions for installing Apache Hadoop on Ubuntu. Follow the instructions below to set up a single-node Hadoop cluster in pseudo-distributed mode.

### Prerequisites

Before proceeding with the installation, ensure that you have the following:

- Ubuntu operating system
- Java Development Kit (JDK) version 8 or higher
- Internet connectivity

### Step 1: Download Hadoop

1. Go to the Hadoop release page on the Apache website.
2. Locate the download URL for Hadoop 3.3.2. For example:
   ```
   https://dlcdn.apache.org/hadoop/common/hadoop-3.3.2/hadoop-3.3.2.tar.gz
   ```
3. Open a terminal and use the `wget` command to download the Hadoop binary. For example:
   ```
   wget https://dlcdn.apache.org/hadoop/common/hadoop-3.3.2/hadoop-3.3.2.tar.gz
   ```
   If you encounter certificate validation errors, you can use the `--no-check-certificate` option with `wget` to skip certificate validation.

### Step 2: Unpack Hadoop

1. Create a directory to store the Hadoop files. For example:
   ```
   mkdir ~/hadoop
   ```
2. Unpack the downloaded Hadoop binary using the following command:
   ```
   tar -xvzf hadoop-3.3.2.tar.gz -C ~/hadoop
   ```
3. Change the current directory to the Hadoop folder:
   ```
   cd ~/hadoop/hadoop-3.3.2/
   ```

### Step 3: Configure passphraseless SSH

1. Ensure that you can SSH to localhost without a passphrase. If necessary, install SSH using the following command:
   ```
   sudo apt install ssh
   ```
2. Generate SSH keys and configure passphraseless SSH by running the following commands:
   ```
   ssh-keygen -t rsa -P '' -f ~/.ssh/id_rsa
   cat ~/.ssh/id_rsa.pub >> ~/.ssh/authorized_keys
   chmod 0600 ~/.ssh/authorized_keys
   ```
   If you encounter connection issues, you may need to restart the SSH service using the following command:
   ```
   sudo service ssh restart
   ```

### Step 4: Configure Pseudo-Distributed Mode

1. Set up environment variables by editing the `~/.bashrc` file:
   ```
   vi ~/.bashrc
   ```
   Add the following lines to the file:
   ```bash
   export JAVA_HOME=/path/to/your/JDK
   export HADOOP_HOME=~/hadoop/hadoop-3.3.2
   export PATH=$PATH:$HADOOP_HOME/bin
   export HADOOP_CONF_DIR=$HADOOP_HOME/etc/hadoop
   ```
   Save the file and exit. Then, run the following command to apply the changes:
   ```
   source ~/.bashrc
   ```

2. Edit the `etc/hadoop/hadoop-env.sh` file:
   ```
   vi etc/hadoop/hadoop-env.sh
   ```
   Set the `JAVA_HOME` environment variable to the path of your JDK installation:
   ```bash
   export JAVA_HOME=/path/to/your/JDK
   ```
   Save the file and exit.

3. Edit the `etc/hadoop/core-site.xml` file:
   ```
   vi etc/hadoop/core-site.xml
   ```
   Add the following configuration inside the `<configuration>` section:
   ```xml
   <property>
       <name>fs.defaultFS</name>
       <value>hdfs://localhost:9000</value>
  
   </property>
   ```
   Save the file and exit.

4. Edit the `etc/hadoop/hdfs-site.xml` file:
   ```
   vi etc/hadoop/hdfs-site.xml
   ```
   Add the following configuration inside the `<configuration>` section:
   ```xml
   <property>
       <name>dfs.replication</name>
       <value>1</value>
   </property>
   <property>
       <name>dfs.namenode.name.dir</name>
       <value>/home/yourusername/hadoop/dfs/name332</value>
   </property>
   <property>
       <name>dfs.datanode.data.dir</name>
       <value>/home/yourusername/hadoop/dfs/data332</value>
   </property>
   ```
   Replace `yourusername` with your actual username and ensure that the specified directory paths exist:
   ```bash
   mkdir -p ~/hadoop/dfs/name332
   mkdir -p ~/hadoop/dfs/data332
   ```
   Save the file and exit.

5. Edit the `etc/hadoop/mapred-site.xml` file:
   ```
   vi etc/hadoop/mapred-site.xml
   ```
   Add the following configuration inside the `<configuration>` section:
   ```xml
   <property>
       <name>mapreduce.framework.name</name>
       <value>yarn</value>
   </property>
   <property>
       <name>mapreduce.application.classpath</name>
       <value>$HADOOP_MAPRED_HOME/share/hadoop/mapreduce/*:$HADOOP_MAPRED_HOME/share/hadoop/mapreduce/lib/*</value>
   </property>
   ```
   Save the file and exit.

6. Edit the `etc/hadoop/yarn-site.xml` file:
   ```
   vi etc/hadoop/yarn-site.xml
   ```
   Add the following configuration inside the `<configuration>` section:
   ```xml
   <property>
       <name>yarn.nodemanager.aux-services</name>
       <value>mapreduce_shuffle</value>
   </property>
   <property>
       <name>yarn.nodemanager.env-whitelist</name>
       <value>JAVA_HOME,HADOOP_COMMON_HOME,HADOOP_HDFS_HOME,HADOOP_CONF_DIR,CLASSPATH_PREPEND_DISTCACHE,HADOOP_YARN_HOME,HADOOP_MAPRED_HOME</value>
   </property>
   ```
   Save the file and exit.

### Step 5: Format Namenode

Run the following command to format the Hadoop namenode:
```
bin/hdfs namenode -format
```

### Step 6: Start Hadoop Daemons

1. Start the NameNode and DataNode daemons by running the following command:
   ```
   sbin/start-dfs.sh
   ```

2. Check the status of the daemons using the `jps` command:
   ```
   jps
   ```

   You should see four processes: `NameNode`, `DataNode`, `SecondaryNameNode`, and `ResourceManager`.

3. Access the Hadoop NameNode web interface by visiting the following URL in your web browser:
   ```
   http://localhost:9870/dfshealth.html#tab-overview
   ```

4. Start the YARN daemon by running the following command:
   ```
   sbin/start-yarn.sh
   ```

5. Check the status of the YARN daemon using the `jps` command:
   ```
   jps
   ```

   You should see two additional processes: `NodeManager` and `ResourceManager`.

6. Access the YARN Resource Manager web interface by visiting the following URL in your web browser:
   ```
   http://localhost:8088

/cluster
   ```

### Step 7: Shutdown Hadoop Services

When you're done using Hadoop, you can shut down the daemons by running the following commands:

```
sbin/stop-yarn.sh
sbin/stop-dfs.sh
```

To verify that the daemons have stopped, use the `jps` command, which should only show a single process.

## Hadoop Word Count

Hadoop is an open-source framework that allows for distributed processing of large datasets across clusters of computers. It utilizes the Hadoop Distributed File System (HDFS) for data storage and the MapReduce programming model for data processing.

### Implementation Steps

1. **Input Splitting**: The input data is divided into chunks called "splits," and each split is assigned to a mapper for further processing.

2. **Mapping**: Each mapper processes its assigned split and generates intermediate key-value pairs. In the word count example, the mapper takes a chunk of text and emits each word as the key and the value 1.

3. **Shuffling**: The intermediate key-value pairs from all the mappers are sorted and grouped by key, ensuring that all the values for a particular key are together.

4. **Reducing**: Each reducer takes a group of key-value pairs with the same key and performs the desired aggregation operation. In the word count example, the reducer sums up the values associated with each word, resulting in the final word count.

### Advantages of Hadoop

- **Distributed Processing**: Hadoop allows for processing large datasets by distributing the workload across multiple machines in a cluster, which significantly improves performance and scalability.

- **Fault Tolerance**: Hadoop is designed to handle failures gracefully. If a machine fails during processing, the framework automatically redistributes the workload to other available machines, ensuring the job's completion.

- **Data Locality**: Hadoop optimizes data processing by bringing computation closer to the data. It attempts to schedule tasks on the same nodes where the data is stored, minimizing network traffic and improving performance.

- **Ecosystem**: Hadoop has a rich ecosystem of tools and libraries that extend its functionality. These include HBase, Hive, Pig, Spark, and many others, providing a wide range of data processing and analysis capabilities.

## Java Word Count

Java is a widely used general-purpose programming language known for its simplicity, reliability, and portability. Implementing word count in Java involves writing code to read input files, tokenize the text, and perform the necessary counting operations.

### Implementation Steps

1. **Reading Input**: Java code reads the input text file(s) and splits them into individual words. This can be achieved using standard Java I/O operations or third-party libraries.

2. **Tokenization**: The input text is tokenized, splitting it into individual words or tokens. Various string manipulation functions or regular expressions can be used for this purpose.

3. **Counting**: Each word is processed, and a count is maintained for each unique word encountered. The count can be stored in a data structure such as a HashMap or a custom implementation.

4. **Output**: Once the counting is complete, the results can be displayed or saved to an output file, depending on the requirements.

### Advantages of Java

- **Simplicity**: Java provides a simple and straightforward programming model for implementing word count. Developers familiar with Java can quickly write and understand the code.

- **Flexibility**: Java offers greater flexibility in terms of customizing the word count implementation to specific requirements. Developers have full control over the code and can easily extend it to handle additional functionalities.

- **Portability**: Java code can be executed on any platform that supports Java Virtual Machine (JVM), making it highly portable across different operating systems.

- **Integrated Development Environment (IDE) Support**: Java benefits from a wide range of powerful IDEs, such as Eclipse and IntelliJ IDEA

, which provide features like code completion, debugging, and profiling, making development more efficient.

## Analysis

We have conducted a comparison between the word count implementation using Hadoop and Java, measuring the execution time in milliseconds for different input file sizes. The chart below presents the results:

![Hadoop vs Java Time and Size Comparison](./docs/comparison.png)

### Observations

1. **Execution Time**: In general, we can observe that the Java implementation outperforms Hadoop in terms of execution time for all tested input file sizes. As the input size increases, the gap between the two implementations becomes more pronounced.

2. **Scalability**: Interestingly, while Hadoop is known for its scalability and ability to handle large datasets, the provided results indicate that the Java implementation performs better across all input sizes. This suggests that for smaller-scale word count tasks, the overhead of setting up and managing a Hadoop cluster might outweigh the performance benefits.

3. **Consistency**: The Java implementation exhibits relatively consistent execution times across different input sizes. On the other hand, the Hadoop implementation shows varying execution times, likely due to the distributed nature of the processing and the associated overhead.

### Considerations

When choosing between Hadoop and Java for word count or similar tasks, several factors should be considered:

- **Data Size**: If dealing with very large datasets that cannot fit into memory, Hadoop's distributed processing capabilities become more advantageous.

- **Infrastructure**: Utilizing Hadoop requires setting up and maintaining a Hadoop cluster, which introduces additional complexity and resource requirements. In contrast, Java can be executed on a single machine or a small cluster without the need for specialized infrastructure.

- **Ecosystem**: If the word count task is part of a larger data processing pipeline that can benefit from other Hadoop ecosystem components, such as Hive, Pig, or Spark, choosing Hadoop might provide a more integrated and comprehensive solution.

- **Development Effort**: Java offers a simpler development experience, particularly for smaller-scale tasks. Hadoop, on the other hand, requires understanding and working with the MapReduce paradigm and related concepts.

It's important to carefully evaluate the specific requirements, constraints, and trade-offs of your use case to make an informed decision between Hadoop and Java for word count or similar data processing tasks.

## Conclusion

Both Hadoop and Java provide distinct approaches to implementing word count functionality. Hadoop excels in handling large-scale data processing tasks by leveraging distributed computing and fault tolerance. On the other hand, Java offers simplicity, flexibility, and portability for smaller-scale tasks or scenarios where the full Hadoop ecosystem is not required.

The choice between Hadoop and Java for word count depends on the specific use case, the scale of data, available resources, and the need for additional functionalities provided by the Hadoop ecosystem.
