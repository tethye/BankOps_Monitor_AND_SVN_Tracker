function initCommitChart(chartData) {
    const ctx = document.getElementById('commitChart').getContext('2d');
    new Chart(ctx, {
        type: 'bar',
        data: {
            labels: chartData.labels,
            datasets: [{
                label: 'Total Commits',
                data: chartData.data,
                backgroundColor: 'rgba(54, 162, 235, 0.5)',
                borderColor: 'rgba(54, 162, 235, 1)',
                borderWidth: 1
            }]
        },
        options: {
            responsive: true,
            scales: {
                y: {
                    beginAtZero: true,
                    title: {
                        display: true,
                        text: 'Number of Commits'
                    }
                },
                x: {
                    title: {
                        display: true,
                        text: 'Users'
                    }
                }
            }
        }
    });
}






// Directory Tree
function loadDirectoryTree(repoUrl, path = '') {
    fetch(`/repo/${repoUrl}/tree?path=${encodeURIComponent(path)}`)
        .then(response => response.json())
        .then(data => renderTree(data));
}

function renderTree(items) {
    const treeContainer = document.getElementById('directory-tree');
    treeContainer.innerHTML = items.map(item => `
        <div class="tree-item ${item.isDirectory ? 'folder' : 'file'}" 
             data-path="${item.path}"
             ondblclick="${item.isDirectory ? 'navigateToPath(this)' : 'showFileDiff(this)'}">
            ${item.name}
        </div>
    `).join('');
}





    function openModal() {
    fetch('/svn-dashboard/modal')
        .then(response => response.text())
        .then(html => {
            document.getElementById('modalContent').innerHTML = html;
            document.getElementById('repoModal').style.display = 'block';


            // setTimeout(() => {
            //     const firstInput = document.querySelector('#repoModal input');
            //     if (firstInput) firstInput.focus();
            // }, 50);

            document.getElementById('repoForm').onsubmit = function(e) {
                e.preventDefault();

                const formData = new FormData(this);
                const data = Object.fromEntries(formData.entries());
                fetch('/svn-dashboard/addrepo', {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify(data)
                }).then(resp => {
                    if (resp.ok) {
                        alert('Repository saved!');
                        closeModal();
                        location.reload();
                    } else {
                        alert('Error saving repository');
                    }
                });
            };
        });
}

    function closeModal() {
    document.getElementById('repoModal').style.display = 'none';
        const modalStyle = document.getElementById("modalStyle");
        if (modalStyle) {
            modalStyle.remove();
        }
    }
function deleteRepository(element) {
    const name = element.getAttribute("data-repo-name");
    if (confirm("Are you sure you want to delete?")) {
        fetch('/svn-dashboard/deleteRepo/' + encodeURIComponent(name), {
            method: 'GET'
        }).then(resp => {
            if (resp.ok) {
                alert('Repository deleted successfully!');
                location.reload();
            } else {
                alert('Something went wrong!');
            }
        });
    }
}


function openChangedFiles(changedFiles, rev) {
    document.getElementById("changefile").style.display = 'block';
    const container = document.getElementById('modal-body');
    container.innerHTML = '';
    const files = changedFiles.split(',');
    files.forEach(file => {
        const fileItem = document.createElement("p");
        fileItem.textContent = file.trim();
        fileItem.style.cursor = "pointer";
        fileItem.style.padding = "4px 0";
        fileItem.style.borderBottom = "1px solid #ccc";

        // Add click handler to open second modal
        fileItem.addEventListener("click", () => {
            openFileDetailModal(file.trim(), rev);
        });

        container.appendChild(fileItem);
    });

}

function openFileDetailModal(file, rev) {
    const url = `/svn-dashboard/diff-viewer?repoUrl=${encodeURIComponent(file)}&rev=${rev}`;

    fetch(url)
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.text(); // HTML page response
        })
        .then(html => {
            // Open the HTML in a modal OR navigate to a new page
            const newWindow = window.open();
            newWindow.document.write(html);
            newWindow.document.close();
        })
        .catch(error => {
            console.error('Error fetching diff:', error);
            alert('Could not load diff page.');
        });
}

function closechangedfile()  {
    document.getElementById("changefile").style.display = 'none';
}


function authorKeypress(fldobj, event){
    if (event.key === "Enter") {
        event.preventDefault(); // Prevent form submission (if any)

        const authorName = fldobj.value;

        // Perform AJAX GET request
        fetch(`/svn-dashboard?authorname=${encodeURIComponent(authorName)}`)
            .then(response => {
                if (!response.ok) {
                    throw new Error("Network response was not ok");
                    console.log('Error fetching diff:', response.text());
                }
                return response.text();// or .json() if expecting JSON
            })
            .then(data => {
                // Handle response: update DOM, display results, etc.
                console.log("Success:", data);
                // For example:
                // document.getElementById("results").innerHTML = data;
            })
            .catch(error => {
                console.error("Error fetching data:", error);
                console.log("Error fetching data:", error);
            });
    }
}